<template>
    <div style="display: flex;line-height: 60px;">
        <div style="margin-top: 8px;">
            <i :class="icon" style="font-size: 20px;cursor: pointer;" @click="collapse"></i>
        </div>
        <div style="flex: 1;text-align: center;font-size: 34px;">
            <span>欢迎使用仓库管理系统</span>
        </div>

        <el-dropdown>
            <span>{{user.name}}</span>
            <i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
                <el-dropdown-item @click.native="logOut">退出登录</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data(){
           return{
               user:JSON.parse(sessionStorage.getItem('CurUser'))
           }
        },
        props: {
            icon: String
        },
        methods: {
            toUser() {
                console.log('toUser')
                this.$router.push("/Home")
            },

            logOut() {
                console.log('logOut')

                this.$confirm('您确定要退出登录吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    center: true
                }).then(() => {
                    this.$message({
                        type: 'success',
                        message: '退出成功!'
                    });
                    this.$router.push("/")
                    sessionStorage.clear()

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消退出'
                    });
                });

            },

            collapse() {
                this.$emit('doCollapse')
            }


        },
        created() {
            this.$router.push('/Home')
        }
    }
</script>

<style scoped>

</style>