package top.carljay.book.config

/**
 * Created by carljay on 2018/1/31.
 */
interface Config {
    companion object {
        //    String TCP_ADDRESS = "carljay.top";
        val TCP_ADDRESS = "192.168.0.107"
        //    String HTTP_ADDRESS = "https://carljay.top:9090/";
        val HTTP_ADDRESS = "http://192.168.0.107:8080/"
        //    String HTTP_ADDRESS = "http://192.168.100.103:8080/";
        val HTTP_GET_LOGIN = HTTP_ADDRESS + "user/login"
        val HTTP_GET_REGISTER = HTTP_ADDRESS + "user/register"
        val HTTP_GET_MAIN_RES = HTTP_ADDRESS + "MainEvent/mainRes"
        val HTTP_GET_ALL_FRIENDS = HTTP_ADDRESS + "friend/get_my_friends?uuid=&?"
        val HTTP_GET_SEARCH_BOOK = HTTP_ADDRESS + "book/search_book?name=&?&type=&?"
    }
}
