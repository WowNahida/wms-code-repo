
import VueRouter from 'vue-router'

const routes =[
    {
        path:'/',
        name:'login',
        component:()=>import('../components/Login')
    },
    {
        path:'/Index',
        name:'index',
        component:()=>import('../components/Index'),
        children:[
            {
                path:'/Home',
                name:'home',
                meta:{
                    title:"首页"
                },
                component:()=>import('../components/Home'),
            },
            /*已换成动态路由
            {
                path:'/Admin',
                name:'admin',
                meta:{
                    title:"管理员管理"
                },
                component:()=>import('../components/admin/AdminManage.vue'),
            },
            {
                path:'/User',
                name:'user',
                meta:{
                    title:"用户操作"
                },
                component:()=>import('../components/user/UserOperate.vue'),
            }*/
        ]
    }
]

const router = new VueRouter(
    {
        mode:'history',
        routes
    }
)

//清空原来的路由
export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes:[]
    }).matcher

}

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(to) {
    return VueRouterPush.call(this,to).catch(err => err)
}

export default router;