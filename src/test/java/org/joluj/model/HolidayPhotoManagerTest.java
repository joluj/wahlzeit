package org.joluj.model;

import org.junit.Test;
//import org.powermock.core.classloader.annotations.PrepareForTest;
import org.wahlzeit.model.PhotoManager;

import static org.junit.Assert.assertTrue;

//@PrepareForTest(PhotoManager.class)
public class HolidayPhotoManagerTest {

  @Test
  public void genericManagerInstanceOfHolidayPhotoManager() {
    var manager = PhotoManager.getInstance();
    assertTrue(manager instanceof HolidayPhotoManager);
  }

//  @Test
//  public void checkPersist() throws Exception {
//    // Setup global configs (local in memory db with h2)
////    SysConfig.setInstance(new SysConfig("./src/main/webapp", ""));
////    SysConfig.getInstance().setValue(SysConfig.DB_CONNECTION, "jdbc:h2:mem:test");
////    SysConfig.getInstance().setValue(SysConfig.DB_USER, "sa");
////    SysConfig.getInstance().setValue(SysConfig.DB_PASSWORD, "");
////    SysConfig.getInstance().setValue(SysConfig.DB_DRIVER, "org.h2.Driver");
//
//    // create session for test
//    var session = new SysSession("test");
//    SessionManager.setThreadLocalSession(session);
//
//    // create main tables
//    var main = new ServiceMain();
//    main.setUpDatabase();
//
//    // create photo
//    var photo = new HolidayPhoto();
//    photo.setCountry("Turkmenistan");
//    photo.setOwnerEmailAddress(EmailAddress.getFromString("test@example.com"));
//
//    var manager = PhotoManager.getInstance();
//    manager.addPhoto(photo);
//    manager.savePhoto(photo);
//
//    // Clear caches
////    manager.clearCache();
//
////    var managerSpy = PowerMockito.spy(manager);
//
////    doReturn(null).when(managerSpy, "doGetPhotoFromId", ArgumentMatchers.any());
////    when(managerSpy, "doGetPhotoFromId", ArgumentMatchers.any()).thenReturn(null);
//
//    var loadedPhoto = manager.getPhotoFromId(photo.getId());
//    assertTrue(loadedPhoto instanceof HolidayPhoto);
////    assertNotEquals(photo, loadedPhoto);
//    assertEquals(photo.getCountry(), ((HolidayPhoto) loadedPhoto).getCountry());
//
//    // cleanup
//    SessionManager.dropThreadLocalSession();
//  }

}
