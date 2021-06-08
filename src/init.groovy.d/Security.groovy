//import hudson.model.User;
//import jenkins.security.ApiTokenProperty;
//import groovy.util.logging.Slf4j
//
//@Slf4j
//class Security {
//
//    static void main(String... args) {
//
//    //You should write down here the name that the user
//        def userName="admin"
//
//        def user = User.get(userName)
//        def apiTokenProperty =  user.getProperty(ApiTokenProperty.class)
//
//
//        apiTokenProperty.changeApiToken()
//        String p = apiTokenProperty.getApiToken();
//
//        log.info p;
//        user.save() ;
//
//
//
//    }
//
//}
