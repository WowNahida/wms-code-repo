<template>
    <div>
        <div style="margin-bottom: 5px;">
            <!--回车的时候查询-->
            <el-input v-model="name" placeholder="请输入货物名" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 5px">
                <el-option
                        v-for="item in storageData"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                </el-option>
            </el-select>
            <el-select v-model="goodstype" placeholder="请选择货物类别" style="margin-left: 5px">
                <el-option
                        v-for="item in goodstypeData"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                </el-option>
            </el-select>

            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
            <el-button type="primary" style="margin-left: 5px;" @click="add" v-if="user.roleId!=2">新增</el-button>
            <el-button type="primary" style="margin-left: 5px;" @click="inStorage" v-if="user.roleId!=2">入库</el-button>
            <el-button type="primary" style="margin-left: 5px;" @click="outStorage" v-if="user.roleId!=2">出库</el-button>

        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ backgroundColor: 'rgba(11,22,6,0.9)' }"
                  border
                  highlight-current-row
                  @current-change="selectCurrentChange"
        >
            <el-table-column prop="id" label="id" width="60">
            </el-table-column>
            <el-table-column prop="name" label="货物名" width="120">
            </el-table-column>
            <el-table-column prop="storage" label="仓库" width="120" :formatter="formatStorage">
            </el-table-column>
            <el-table-column prop="goodstype" label="货物分类" width="120" :formatter="formatGoodsType">
            </el-table-column>
            <el-table-column prop="count" label="货物数量" width="120">
            </el-table-column>
            <el-table-column prop="remark" label="备注">
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
            <el-table-column prop="operate" label="操作" v-if="user.roleId!=2">
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
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="货物名" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>

                <el-form-item label="仓库" prop="storage">
                    <el-col :span="20">
                        <el-select v-model="form.storage" placeholder="请选择仓库">
                            <el-option
                                    v-for="item in storageData"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>

                <el-form-item label="货物分类" prop="goodstype">
                    <el-col :span="20">
                        <el-select v-model="form.goodstype" placeholder="请选择货物类名">
                            <el-option
                                    v-for="item in goodstypeData"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>

                <el-form-item label="货物数量" prop="count">
                    <el-col :span="20">
                        <el-input v-model="form.count"></el-input>
                    </el-col>
                </el-form-item>

                <el-form-item label="备注" prop="remark">
                    <el-col :span="20">
                        <el-input type="textarea" v-model="form.remark"></el-input>
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


        <el-dialog
                title="出入库"
                :visible.sync="selectDialogVisible"
                width="30%"
                center>

            <el-dialog
                    width="50%"
                    title="选择用户"
                    :visible.sync="innerVisible"
                    append-to-body>
                <SelectUser @doSelectUser="doSelectUser"></SelectUser>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="innerVisible = false">取 消</el-button>
                    <el-button type="primary" @click="confirmUser">确 定</el-button>
                </span>
            </el-dialog>

            <el-form ref="storeform" :model="storeform" :rules="strules" label-width="80px">
                <el-form-item label="货物名">
                    <el-col :span="20">
                        <el-input v-model="storeform.goodsname" readonly></el-input>
                    </el-col>
                </el-form-item>

                <el-form-item label="经办人">
                    <el-col :span="20">
                        <el-input v-model="storeform.username" readonly @click.native="selectUser"></el-input>
                    </el-col>
                </el-form-item>


                <el-form-item label="货物数量" prop="count">
                    <el-col :span="20">
                        <el-input v-model="storeform.count"></el-input>
                    </el-col>
                </el-form-item>

                <el-form-item label="经办人" prop="userid">
                    <el-col :span="20">
                        <el-input v-model="storeform.userid"></el-input>
                    </el-col>
                </el-form-item>

                <el-form-item label="操作人" prop="adminId">
                    <el-col :span="20">
                        <el-input v-model="storeform.adminId"></el-input>
                    </el-col>
                </el-form-item>

                <el-form-item label="备注" prop="remark">
                    <el-col :span="20">
                        <el-input type="textarea" v-model="storeform.remark"></el-input>
                    </el-col>
                </el-form-item>
                <!--<el-form-item label="角色" prop="roleId">
                    <el-col :span="20">
                        <el-input v-model="form.roleId"></el-input>
                    </el-col>
                </el-form-item>-->
            </el-form>
            <span slot="footer" class="dialog-footer">
    <el-button @click="selectDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doInStorage">确 定</el-button>
  </span>
        </el-dialog>
    </div>

</template>

<script>
    import SelectUser from "@/components/user/SelectUser";

    export default {
        name: "GoodsManage",
        components: {SelectUser},
        data() {

            let checkCount = (rule, value, callback) => {
                if (value > 9999) {
                    callback(new Error('数量输入过大'));
                } else {
                    callback();
                }
            };

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
                user: JSON.parse(sessionStorage.getItem('CurUser')),
                storageData: [],
                goodstypeData: [],
                tableData: [],
                pageSize: 10,
                pageNum: 1,
                total: 0,
                storage: '',
                goodstype: '',
                name: '',
                //Dialog对话框居中可视化默认不开启
                currentRow: {},
                tempUser: '',
                centerDialogVisible: false,
                selectDialogVisible: false,
                innerVisible: false,
                //新增成员的默认表单
                form: {
                    id: '',
                    name: '',
                    storage: '',
                    goodstype: '',
                    count: '',
                    remark: ''

                    //roleId: '2'

                },

                storeform: {
                    goods: '',
                    goodsname: '',
                    count: '',
                    username: '',
                    userid: '',
                    adminId: '',
                    remark: '',
                    action: '1'

                    //roleId: '2'

                },
                //form表单输入数据的规则
                rules: {
                    name: [
                        {required: true, message: '请输入货物名', trigger: 'blur'},
                        /*{validator: checkName, message: '仓库名不可以与已有的重名', trigger: 'blur'}*/
                    ],
                    storage: [
                        {required: true, message: '请选择仓库', trigger: 'blur'},
                    ],
                    goodstype: [
                        {required: true, message: '请选择货物钟类', trigger: 'blur'},
                    ],
                    count: [
                        {required: true, message: '请输入数量', trigger: 'blur'},
                        {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数', trigger: 'blur'},
                        {validator: checkCount, trigger: 'blur'}
                    ]

                },

                strules: {}

            }
        },
        methods: {

            //内层dialog选择用户
            selectUser() {
                this.innerVisible = true;
            },

            doSelectUser(val) {
                console.log(val)
                this.tempUser = val
            },

            confirmUser() {
                this.storeform.userid = this.tempUser.id
                this.storeform.username = this.tempUser.name
                this.innerVisible = false;

            },

            selectCurrentChange(val) {
                this.currentRow = val;
            },

            formatStorage(row) {
                let temp = this.storageData.find(item => {
                    return item.id == row.storage
                })

                return temp && temp.name
            },
            formatGoodsType(row) {
                let temp = this.goodstypeData.find(item => {
                    return item.id == row.goodstype
                })

                return temp && temp.name
            },
            //清空表单数据
            resetForm() {
                this.$refs.form.resetFields();
            },

            //清空出入库表单数据
            resetStoreForm() {
                this.$refs.storeform.resetFields();
            },
            //修改信息
            mod(row) {
                console.log(row)

                this.centerDialogVisible = true
                this.$nextTick(() => {
                    //把行内数据赋值到表单
                    this.form.id = row.id
                    this.form.name = row.name
                    this.form.storage = row.storage
                    this.form.goodstype = row.goodstype
                    this.form.count = row.count
                    this.form.remark = row.remark
                    //this.form.roleId = row.roleId
                })


            },
            //删除信息
            del(id) {
                console.log(id)
                this.$axios.get(this.$httpUrl + '/goods/del?id=' + id)
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
            //新增货物
            add() {
                this.centerDialogVisible = true
                this.$nextTick(() => {
                    this.resetForm()
                    /*resetForm没有清空id*/
                    this.form.id = ''
                })
            },

            //货物入库
            inStorage() {
                if (!this.currentRow.id) {
                    alert("请点击单行范围内的记录")
                    return ""
                }
                this.selectDialogVisible = true;
                this.$nextTick(() => {
                    this.resetStoreForm()
                    /*resetForm没有清空id*/

                })

                this.storeform.goodsname = this.currentRow.name
                this.storeform.goods = this.currentRow.id
                this.storeform.adminId = this.user.id
                this.storeform.action = '1'
            },
            outStorage() {
                if (!this.currentRow.id) {
                    alert("请点击单行范围内的记录")
                    return ""
                }
                this.selectDialogVisible = true;
                this.$nextTick(() => {
                    this.resetStoreForm()
                    /*resetForm没有清空id*/

                })

                this.storeform.goodsname = this.currentRow.name
                this.storeform.goods = this.currentRow.id
                this.storeform.adminId = this.user.id
                this.storeform.action = '2'
            },

            doInStorage() {
                this.$axios.post(this.$httpUrl + '/record/save', this.storeform)
                    .then(res => res.data).then(res => {
                    console.log(res)
                    if (res.code == 200) {

                        this.$message({
                            message: '出入库成功',
                            type: 'success'
                        });
                        this.selectDialogVisible = false
                        this.loadPost()
                        this.resetStoreForm()
                    } else {
                        this.$message({
                            message: '出入库失败',
                            type: 'error'
                        })
                    }
                })
            },

            doMod() {
                this.$axios.post(this.$httpUrl + '/goods/update', this.form)
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
                this.$axios.post(this.$httpUrl + '/goods/save', this.form)
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
                this.$axios.post(this.$httpUrl + '/goods/listPage', {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    param: {
                        name: this.name,
                        //int转化成String
                        storage: this.storage + '',
                        goodstype: this.goodstype + ''

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
            loadStorage() {
                this.$axios.get(this.$httpUrl + '/storage/list').then(res => res.data).then(res => {
                    console.log(res)
                    if (res.code == 200) {
                        this.storageData = res.data;
                    } else {
                        alert("获取数据失败")
                    }

                })
            },
            loadGoodsType() {
                this.$axios.get(this.$httpUrl + '/goodstype/list').then(res => res.data).then(res => {
                    console.log(res)
                    if (res.code == 200) {
                        this.goodstypeData = res.data;
                    } else {
                        alert("获取数据失败")
                    }

                })
            },
            resetParam() {
                this.name = ''
                this.storage = ''
                this.goodstype = ''
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
            this.loadStorage()
            this.loadGoodsType()
            this.loadPost()
        }
    }
</script>

<style scoped>

</style>