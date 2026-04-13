import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class generate_bcrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "admin123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Original password: " + rawPassword);
        System.out.println("Encoded password: " + encodedPassword);
        
        // Verify
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("Password matches: " + matches);
    }
}
