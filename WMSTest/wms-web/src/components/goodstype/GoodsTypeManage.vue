<template>
    <div>
        <div style="margin-bottom: 5px;">
            <!--回车的时候查询-->
            <el-input v-model="name" placeholder="请输入货物分类名" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>

            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
            <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>

        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ backgroundColor: 'rgba(11,22,6,0.9)' }"
                  border
        >
            <el-table-column prop="id" label="id" width="60">
            </el-table-column>
            <el-table-column prop="name" label="货物分类名" width="120">
            </el-table-column>
            <el-table-column prop="remark" label="备注" >
            </el-table-column>
            <!--<el-table-column prop="roleId" label="角色" width="120">
                <template slot-scope="scope">
                    <el-tag
                            :type="scope.row.roleId === 0 ? 'danger':(scope.row.roleId === 1 ? 'primary' : 'success')"
                            disable-transitions>{{scope.row.roleId ===0 ?'超级管理员':
                        (scope.row.roleId === 1 ? '管理员' : '用户')}}
                    </el-tag>
                </template>
            </el-table-column>-->
            <el-table-column prop="operate" label="操作">
                <template slot-scope="scope">
                    <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
                    <el-popconfirm
                            title="确定删除吗？"
                            @confirm="del(scope.row.id)"
                            style="margin-left: 5px"
                    >
                        <el-button slot="reference" size="small" type="danger">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20, 30]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>

        <el-dialog
                title="提示"
                :visible.sync="centerDialogVisible"
                width="30%"
                center>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="货物分类名" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-col :span="20">
                        <el-input type= "textarea" v-model="form.remark"></el-input>
                    </el-col>
                </el-form-item>
                <!--<el-form-item label="角色" prop="roleId">
                    <el-col :span="20">
                        <el-input v-model="form.roleId"></el-input>
                    </el-col>
                </el-form-item>-->
            </el-form>
            <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
        </el-dialog>
    </div>

</template>

<script>
    export default {
        name: "GoodstypeManage",
        data() {

            // *****判断仓库名是否重复*****
            /* let checkName = (rule, value, callback) => {
                 this.$axios.get(this.$httpUrl + "/storage/findByName?name=" + this.form.name).then(res => res.data).then(res => {
                     if (res.code != 200) {
                         callback();
                     } else {
                         callback(new Error('仓库名已存在'));
                     }

                 })
             };*/

            return {
                tableData: [],
                pageSize: 10,
                pageNum: 1,
                total: 0,
                name: '',
                //Dialog对话框居中可视化默认不开启
                centerDialogVisible: false,
                //新增成员的默认表单
                form: {
                    id: '',
                    name: '',
                    remark: ''
                    //roleId: '2'

                },
                //form表单输入数据的规则
                rules: {
                    name: [
                        {required: true, message: '请输入分类名', trigger: 'blur'},
                        /*{validator: checkName, message: '仓库名不可以与已有的重名', trigger: 'blur'}*/
                    ]

                }

            }
        },
        methods: {
            //清空表单数据
            resetForm() {
                this.$refs.form.resetFields();
            },
            //修改信息
            mod(row) {
                console.log(row)

                this.centerDialogVisible = true
                this.$nextTick(() => {
                    //把行内数据赋值到表单
                    this.form.id = row.id
                    this.form.name = row.name
                    this.form.remark = row.remark
                    //this.form.roleId = row.roleId
                })


            },
            //删除信息
            del(id) {
                console.log(id)
                this.$axios.get(this.$httpUrl + '/goodstype/del?id=' + id)
                    .then(res => res.data).then(res => {
                    console.log(res)
                    if (res.code == 200) {

                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.loadPost()
                    } else {
                        this.$message({
                            message: '删除失败',
                            type: 'error'
                        })
                    }
                })
            },
            //新增用户
            add() {
                this.centerDialogVisible = true
                this.$nextTick(() => {
                    this.resetForm()
                })
            },
            doMod() {
                this.$axios.post(this.$httpUrl + '/goodstype/update', this.form)
                    .then(res => res.data).then(res => {
                    console.log(res)
                    if (res.code == 200) {

                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                        this.centerDialogVisible = false
                        this.loadPost()
                        this.resetForm()
                    } else {
                        this.$message({
                            message: '修改失败',
                            type: 'error'
                        })
                    }
                })

            },
            doSave() {
                this.$axios.post(this.$httpUrl + '/goodstype/save', this.form)
                    .then(res => res.data).then(res => {
                    console.log(res)
                    if (res.code == 200) {

                        this.$message({
                            message: '新建成功',
                            type: 'success'
                        });
                        this.centerDialogVisible = false
                        this.loadPost()
                        this.resetForm()
                    } else {
                        this.$message({
                            message: '新建失败',
                            type: 'error'
                        })
                    }
                })
            },
            //确认新增用户
            save() {
                //检查是否违反规则
                this.$refs.form.validate((valid) => {
                    //不违反请求保存
                    if (valid) {

                        if (this.form.id) {
                            this.doMod();
                        } else {
                            this.doSave();
                        }
                    } else { //违反发save请求
                        console.log('error submit!!');
                        return false;
                    }

                });

            },

            loadPost() {
                this.$axios.post(this.$httpUrl + '/goodstype/listPage', {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    param: {
                        name: this.name,
                    }
                }).then(res => res.data).then(res => {
                    console.log(res)
                    if (res.code == 200) {
                        this.tableData = res.data;
                        this.total = res.total;
                    } else {
                        alert("获取数据失败")
                    }

                })
            }
            ,
            resetParam() {
                this.name = ''
            }
            ,
            //处理页面数据条数变化
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pageNum = 1,
                    this.pageSize = val,
                    this.loadPost()
            }
            ,
            //处理当前页面变化
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.pageNum = val,
                    this.loadPost()
            }

        },
        beforeMount() {
            this.loadPost()
        }
    }
</script>

<style scoped>

</style>