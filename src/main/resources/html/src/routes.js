import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import tInfoList from './views/tInfo/tInfoList.vue'
import addtInfo from './views/tInfo/addtInfo.vue'
import lineList from './views/tInfo/lineList.vue'
import orderList from './views/order/orderList.vue'
import rateList from './views/rate/rateList.vue'
import orderInfo from './views/order/orderInfo'
import userList from './views/system/userList.vue'
import adminList from './views/system/adminList.vue'
import homeSwiper from './views/system/homeSwiper.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
 {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '景点管理',
        iconCls: 'el-icon-menu',//图标样式class
        children: [
             { path: '/tInfoList', component: tInfoList, name: '景点管理' },
             { path: '/addtInfo', component: addtInfo, name: '编辑景点' },
             { path: '/rateList', component: rateList, name: '评价管理' },
             { path: '/lineList', component: lineList, name: '推荐路线' }
            
        ]
    },
    {
        path: '/',
        component: Home,
        name: '订单管理',
        iconCls: 'el-icon-setting',
        children: [
            { path: '/orderList', component: orderList, name: '订单管理' },
            { path: '/orderInfo', component: orderInfo, name: '订单统计' }
         ]
    },
    {
        path: '/',
        component: Home,
        name: '系统管理',
        iconCls: 'el-icon-setting',
        children: [
            { path: '/userList', component: userList, name: '游客人员'},
            { path: '/adminList', component: adminList, name: '游客人员'},
            { path: '/homeSwiper', component: homeSwiper, name: '首页轮播'}
         ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;