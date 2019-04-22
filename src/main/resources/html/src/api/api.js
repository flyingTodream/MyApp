import axios from 'axios';

//let base = 'http://192.168.1.126:1111'+'/api';
//let baseurl = 'http://10.156.5.49:1111'+'/api';
let baseurl = 'http://localhost'+'/api';
//登录
export const requestLogin = params => { return axios.post(`${baseurl}/admin/doLogin`, params).then(res => res.data); };
//检查tiken状态
export const checkLogin = params => { return axios.post(`${baseurl}/admin/isLogin`, params).then(res => res.data); };
//修改密码
export const doModifyPassword = params => { return axios.post(`${baseurl}/doModifyPassword`, params).then(res => res.data); };
//获取景点列表
export const getInfoList = params => { return axios.post(`${baseurl}/admin/getInfoList`, params); };
//删除景点信息
export const deltInfo = params => { return axios.post(`${baseurl}/admin/deltInfo`, params); };
//添加景点
export const addInfo = params => { return axios.post(`${baseurl}/admin/addInfo`, params); };
//获取景点信息
export const getTInfo = params => { return axios.post(`${baseurl}/admin/getTInfo`, params); };
//获取轮播图
export const getImgList = params => { return axios.post(`${baseurl}/admin/getImgList`, params); };
//删除轮播图
export const delImg = params => { return axios.post(`${baseurl}/admin/delImg`, params); };
//更新景点信息
export const updateInfo = params => { return axios.post(`${baseurl}/admin/updateInfo`, params); };
//获取路线集合
export const getLineList = params => { return axios.post(`${baseurl}/admin/getLineList`, params); };
//增加路线
export const addLine = params => { return axios.post(`${baseurl}/admin/addLine`, params); };
//删除路线
export const delLine = params => { return axios.post(`${baseurl}/admin/delLine`, params); };
//获取路线详细信息
export const getlineDetail = params => { return axios.post(`${baseurl}/admin/getlineDetail`, params); };
//更新路线详细信息
export const updateLineDetail = params => { return axios.post(`${baseurl}/admin/updateLineDetail`, params); };
//更新路线详细信息
export const delLineDetail = params => { return axios.post(`${baseurl}/admin/delLineDetail`, params); };
//增加路线详细信息
export const addLineDetail = params => { return axios.post(`${baseurl}/admin/addLineDetail`, params); };
//获取评价信息
export const getRateList = params => { return axios.post(`${baseurl}/admin/getRateList`, params); };
//获取评价信息
export const getOrderList = params => { return axios.post(`${baseurl}/admin/getOrderList`, params); };
//获取订单统计列表
export const getOrderSum = params => { return axios.post(`${baseurl}/admin/getOrderSum`, params); };
//获取用户列表
export const getUserList = params => { return axios.post(`${baseurl}/admin/getUserList`, params); };
//删除用户用户
export const delUser = params => { return axios.post(`${baseurl}/admin/delUser`, params); };
//添加景点管理员
export const addAdmin = params => { return axios.post(`${baseurl}/admin/addAdmin`, params); };

//获取首页轮播图
export const getHomeImgList = params => { return axios.post(`${baseurl}/admin/getHomeImgList`, params); };

//删除首页轮播图
export const delHomeImg = params => { return axios.post(`${baseurl}/admin/delHomeImg`, params); };


//获取用户菜单
export const getUserMenu = params => { return axios.post(`${baseurl}/system/user/getUserMenu`, params); };
