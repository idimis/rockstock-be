@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReferralService referralService;
    private final EventRepository eventRepository;

    private static final String DEFAULT_PROFILE_ICON_URL = "https://img.icons8.com/?size=100&id=tZuAOUGm9AuS&format=png&color=000000";

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ReferralService referralService,
                       EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.referralService = referralService;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public User createUser(CreateUserRequestDTO createUserRequestDTO) {
        validateRole(createUserRequestDTO.getRole());

        User user = mapToUserEntity(createUserRequestDTO);
        user.setIsVerified(false); // Set default verification status to false
        User savedUser = userRepository.save(user);

        // Handle referral code if provided
        if (createUserRequestDTO.getReferralCode() != null) {
            referralService.handleReferral(createUserRequestDTO.getReferralCode(), savedUser);
        }

        return savedUser;
    }

    @Transactional
    public void verifyEmail(String email) {
        User user = userRepository.findByEmailContainsIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (user.getIsVerified()) {
            throw new IllegalArgumentException("User is already verified");
        }

        user.setIsVerified(true); // Update verification status
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findByIdAndIsVerifiedTrue(id)
                .orElseThrow(() -> new RuntimeException("User not found or not verified"));
    }

    public User updateUser(Long userId, UpdateUserRequestDTO updateUserRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updateUserRequestDTO.getName() != null && !updateUserRequestDTO.getName().isEmpty()) {
            user.setName(updateUserRequestDTO.getName());
        }

        if (updateUserRequestDTO.getEmail() != null && !updateUserRequestDTO.getEmail().isEmpty()) {
            if (userRepository.existsByEmail(updateUserRequestDTO.getEmail())) {
                throw new RuntimeException("Email is already in use");
            }
            user.setEmail(updateUserRequestDTO.getEmail());
            user.setIsVerified(false); // Reset verification status for new email
        }

        if (updateUserRequestDTO.getPassword() != null && !updateUserRequestDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateUserRequestDTO.getPassword()));
        }

        if (updateUserRequestDTO.getPhotoProfileUrl() != null) {
            validatePhotoProfile(updateUserRequestDTO.getPhotoProfileUrl());
            user.setPhotoProfileUrl(updateUserRequestDTO.getPhotoProfileUrl());
        }

        user.setWebsite(updateUserRequestDTO.getWebsite());
        user.setPhoneNumber(updateUserRequestDTO.getPhoneNumber());
        user.setAddress(updateUserRequestDTO.getAddress());

        return userRepository.save(user);
    }

    private void validatePhotoProfile(String photoUrl) {
        if (!photoUrl.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            throw new IllegalArgumentException("Invalid photo format. Allowed formats: jpg, jpeg, png, gif.");
        }
        // Additional logic for size validation can be added here
    }
}
