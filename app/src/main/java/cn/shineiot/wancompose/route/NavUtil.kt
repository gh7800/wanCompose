package cn.shineiot.wancompose.route

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.ComposeNavigator
import cn.shineiot.wancompose.utils.LogUtil

/**
 * author:Xcy
 * date:2022/5/7 22
 * description: 跳转工具类
 *
 * 使用方法:(声明)
 *     val navHostController = rememberNavController()
 *
 *     NavUtil.get().init(navHostController = navHostController)
 *
 *     NavHost(navController = navHostController, startDestination = RouteConfig.MAIN_PAGE) {
 *         composableX(RouteConfig.MAIN_PAGE) { MainPage() }
 *         composableX(RouteConfig.FIRST_PAGE,
 *              params = listOf(
 *              NavParam("key1", isRequired = false, defaultValue = "default value"),
 *              NavParam("key2"))
 *         ) {
 *              FirstPage(it.arguments?.getString("key1"), it.arguments?.getString("key2"))
 *           }
 *        composableX(RouteConfig.SECOND_PAGE) { SecondPage() }
 *     }
 *
 *默认跳转: NavUtil.get().navigation(RouteConfig.SECOND_PAGE)
 *带参跳转: NavUtil.get().navigation(baseRoute, params = hashMapOf<String, String>().apply {
 *          put("key1", "hello key1")
 *          put("key2", "hello key2")
 *        }))
 **/
const val NavUtilTAG = "NavUtilTAG"

class NavUtil private constructor() {

    lateinit var navHostController: NavHostController
    private var baseRouteInfo = HashMap<String, List<NavParam>>()

    companion object {
        private val util by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { NavUtil() }
        fun get(): NavUtil = util
    }

    /**
     * 初始化
     */
    fun init(navHostController: NavHostController) {
        this.navHostController = navHostController
    }

    /**
     * 为指定路由绑定需要传递的参数
     * @param baseRoute 未拼接或处理的路由
     * @param params 当前参数列表
     */
    fun bindParam(baseRoute: String, params: List<NavParam>) {
        baseRouteInfo[baseRoute] = params
        Log.e(NavUtilTAG, "路由:${baseRoute} 参数:${params}")
    }

    /**
     * 获取当前baseRoute下绑定的参数列表
     */
    private fun getParam(baseRoute: String): List<NavParam> {
        if (baseRouteInfo.containsKey(baseRoute)) {
            val params = baseRouteInfo[baseRoute]
            if (params?.isNotEmpty()!!) {
                return params
            }
        }
        return emptyList()
    }

    /**
     * 跳转
     * @param baseRoute 未拼接或处理的路由
     */
    fun navigation(baseRoute: String, params: HashMap<String, String> = hashMapOf()) {
        val newRoute = StringBuilder()
        newRoute.append(baseRoute)
        //获取从bindParam()绑定的参数列表,拼接出一个合法的路由(对应composableX()中处理后的路由)
        val baseParams = getParam(baseRoute = baseRoute)
        if (baseParams.isNotEmpty()) {//当前基础路由绑定的参数列表不为空
            newRoute.append("/")
            baseParams.forEachIndexed { index, navParam ->
                //拼接参数
                if (navParam.isRequired) {//(必选参数,如果没有传递则直接报错)
                    if (params.containsKey(navParam.key)) {
                        newRoute.append(params[navParam.key])
                    } else {
                        Log.e(
                            NavUtilTAG,
                            "navigation error(route:${baseRoute}  ${navParam.key}为${navParam.isRequired}参数)"
                        )
                    }
                } else {
                    if (params.containsKey(navParam.key)) {//当前非必选参数不为空
                        newRoute.append(params[navParam.key])
                    } else {//当前参数为非必选，如果没有传递则直接补充为默认值
                        newRoute.append(navParam.defaultValue)
                    }
                }
                if (index < baseParams.size - 1) {
                    newRoute.append("/")
                }
            }
        } else {//判断当前的参数列表是否存在，或是否全是非必选的字段
            val isEmpty = paramsIsEmpty(baseRoute)//是否允许为空
            if (!isEmpty) {
                printRequiredParam(baseRoute = baseRoute)
            }
        }
        Log.e(NavUtilTAG, "转换后的路由:${newRoute}")
        try {
            navHostController.navigate(newRoute.toString())
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(NavUtilTAG, "navigate error:${e.toString()}")
        }
    }

    /**
     * 检测当前的路由是否存在参数列表
     * 如果存在则判断字段是否全部为非必选
     * @param baseRoute 未拼接或处理的路由
     * @return 当前参数列表全部为非必选参数或为空时 返回true
     */
    private fun paramsIsEmpty(baseRoute: String): Boolean {
        if (baseRouteInfo.containsKey(baseRoute)) {//有参数列表
            val params = baseRouteInfo[baseRoute]!!
            if (params.isNotEmpty()) {//不为空则遍历当前字段是否全部为未拼接
                params.forEach {
                    if (it.isRequired) {
                        return false
                    }
                }
                //当前所有字段全部为非必选
                return true
            } else {
                return false
            }
        } else {
            return true
        }
    }

    /**
     * 打印必传参数
     */
    private fun printRequiredParam(baseRoute: String) {
        val sb = StringBuilder()
        if (baseRouteInfo.containsKey(baseRoute)) {//有参数列表
            val params = baseRouteInfo[baseRoute]!!
            if (params.isNotEmpty()) {//参数列表不为空
                params.forEach {
                    if (it.isRequired) {//当前路由下必传的参数列表
                        sb.append("${it.key}")
                        sb.append("   ")
                    }
                }
            }
            Log.e(NavUtilTAG, "[baseRoute=${baseRoute} 需要传递:${sb}]")
        }
    }

    //出栈
    fun onBack(){
        val result = navHostController.popBackStack()
        LogUtil.e(result)
    }
}

/**
 * @param baseRoute 当前需要跳转的路由(未拼接或处理的路由)
 * @param params 当前的参数
 * @param content 当前跳转的页面
 */
fun NavGraphBuilder.composableX(
    baseRoute: String,
    params: List<NavParam> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    val newRoute = StringBuilder()
    var newParam = mutableListOf<NamedNavArgument>()
    newRoute.append(baseRoute)

    //将当前基础路由下绑定参数列表
    NavUtil.get().bindParam(baseRoute, params = params)
    if (params.isNotEmpty()) {//当前参数不为空则拼接路由
        newRoute.append("/")
        params.forEachIndexed { index, item ->
            if (index < params.size) {
                //拼接路由
                newRoute.append("{${item.key}}")
                //拼接参数列表
                if (item.isRequired) {
                    newParam.add(navArgument(item.key) { type = NavType.StringType })
                } else {
                    newParam.add(navArgument(item.key) { item.defaultValue })
                }
            }
            if (index < params.size - 1) {
                newRoute.append("/")
            }
        }
    }

    addDestination(
        ComposeNavigator.Destination(provider[ComposeNavigator::class], content).apply {
            this.route = newRoute.toString()
            newParam.forEachIndexed { index, item ->
                addArgument(item.name, item.argument)
            }
        }
    )

    //Log.e(NavUtilTAG, "拼接的路由:${newRoute}")
    //Log.e(NavUtilTAG, "参数:${params}")
}

/**
 * 路由参数
 * @param key 参数名
 * @param isRequired 当前参数是否为必选参数
 * @param defaultValue isRequired为false(非必传参数时) defaultValue必传
 */
data class NavParam(
    val key: String,
    var isRequired: Boolean = true,
    var defaultValue: String = ""
)
