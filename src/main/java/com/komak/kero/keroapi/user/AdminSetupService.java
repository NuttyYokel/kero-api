package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.Role;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdminSetupService {

  private static final Logger LOG = Logger.getLogger(AdminSetupService.class);

  @Autowired
  public AdminSetupService(UserService userService, @Value("${admin.service.inviter}") String inviter)
      throws IOException {
    InputStream resourceInputStream = null;

    try {
      if (userService.getUserCount() == 0) {
        Resource resource = new ClassPathResource("admins");
        resourceInputStream = resource.getInputStream();
        Scanner scanner = new Scanner(resourceInputStream);
        while (scanner.hasNextLine()) {
          User user = new User();
          String[] line = scanner.nextLine().split("~");
          user.setEmail(line[0]);
          user.setFirstName(line[1]);
          user.setRole(Role.ROLE_ADMIN);
          userService.invite(user, inviter);
        }
      }
    }
    catch (IOException e) {
      LOG.error("Invalid admin setup file.", e);
      throw e;
    }
    finally {
      try {
        if (resourceInputStream != null) {
          resourceInputStream.close();
        }
      }
      catch (Exception e) {
        LOG.error("IO error", e);
      }
    }
  }
}
