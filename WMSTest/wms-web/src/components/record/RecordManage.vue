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

        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ backgroundColor: 'rgba(11,22,6,0.9)' }"
                  border
        >
            <el-table-column prop="id" label="id" width="60">
            </el-table-column>
            <el-table-column prop="goodsname" label="货物名" width="120">
            </el-table-column>
            <el-table-column prop="storagename" label="仓库" width="120">
            </el-table-column>
            <el-table-column prop="goodstypename" label="货物分类" width="120">
            </el-table-column>
            <el-table-column prop="username" label="经办人" width="120">
            </el-table-column>
            <el-table-column prop="adminname" label="操作人" width="120">
            </el-table-column>
            <el-table-column prop="count" label="货物数量" width="120">
            </el-table-column>
            <el-table-column prop="createtime" label=操作时间 width="150">
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


    </div>

</template>

<script>
    export default {
        name: "RecordManage",
        data() {

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

                //成员的默认表单
                form: {
                    id: '',
                    goods: '',
                    storage: '',
                    goodstype: '',
                    userid: '',
                    adminId: '',
                    count: '',
                    remark: ''
                    //新增
                    //roleId: '2'

                },

            }

        },

        methods: {


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

            loadPost() {
                this.$axios.post(this.$httpUrl + '/record/listPage', {
                    pageSize: this.pageSize,
                    pageNum: this.pageNum,
                    param: {
                        name: this.name,
                        //int转化成String
                        storage: this.storage + '',
                        goodstype: this.goodstype + '',
                        roleId: this.user.roleId + '',
                        userId: this.user.id + ''

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