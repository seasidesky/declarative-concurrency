package declarative_concurrency;

import declarative_concurrency.user.User;
import declarative_concurrency.user.UserService;
import javaslang.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.function.Consumer;

import static java.util.Arrays.asList;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

  private final static List<String> CWIDS = asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
  private static final Consumer<Either<String, User>> PRINT_RESULT = maybeUser -> log.info("result = {}", maybeUser);

  private final UserService userService;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    log.info("#######################");
    log.info("# loadUsersSequential #");
    log.info("#######################");

    userService.loadUsersSequential(CWIDS).forEach(PRINT_RESULT);

    log.info("###########################");
    log.info("# loadUsersParallelStream #");
    log.info("###########################");

    userService.loadUsersParallelStream(CWIDS).forEach(PRINT_RESULT);

    log.info("#########################");
    log.info("# loadUsersForkJoinPool #");
    log.info("#########################");

    userService.loadUsersForkJoinPool(CWIDS).forEach(PRINT_RESULT);

    log.info("############################");
    log.info("# loadUsersExecutorService #");
    log.info("############################");

    userService.loadUsersExecutorService(CWIDS).forEach(PRINT_RESULT);

    log.info("##############################");
    log.info("# loadUsersCompletableFuture #");
    log.info("##############################");

    userService.loadUsersCompletableFuture(CWIDS).forEach(PRINT_RESULT);

    log.info("#################");
    log.info("# loadUsersFlux #");
    log.info("#################");

    userService.loadUsersFlux(CWIDS).forEach(PRINT_RESULT);
  }
}
