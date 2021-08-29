package Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexChecker {
    public static boolean isValidEmail(String email)
    {
        String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        Pattern p = Pattern.compile(regex);
        String email1 = ""+ email;
        Matcher m = p.matcher(email1);

        return m.matches();
    }
}
