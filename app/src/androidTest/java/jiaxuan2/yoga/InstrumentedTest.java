//package jiaxuan2.yoga;
//
//
//import android.icu.text.DateFormat;
//import android.support.annotation.NonNull;
//import android.support.test.runner.AndroidJUnit4;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import java.util.Date;
//import java.util.concurrent.*;
//import jiaxuan2.yoga.BaseClass.*;
//
//import static org.junit.Assert.*;
//
///**
// * Instrumentation test, which will execute on an Android device.
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(AndroidJUnit4.class)
//public class InstrumentedTest {
//
//    private String testUID = "PTSQeXw7KidrObDIsa7rexeekUc2";
//    private String date = DateFormat.getDateInstance().format(new Date());
////    private User testUser = new User("testAddUser");
//
//    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    DatabaseReference databaseReference = firebaseDatabase.getReference();
//    DatabaseReference testReference = firebaseDatabase.getReference("users/" + testUID);
//
//    int numOfUsers;
//
////    @Test
////    public void testReadAUser() throws Exception {
////        final CountDownLatch readSignal = new CountDownLatch(1);
////
////        final User currentUser = new User("test9");
////        currentUser.addData(new PracticeData(DateFormat.getDateInstance().format(new Date()), "0"));
////
////        testReference.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                assertEquals(currentUser,dataSnapshot.getValue(User.class));
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        });
////
////        readSignal.await(1, TimeUnit.SECONDS);
////    }
//
//
//
//    @Test
//    public void testReadRank() throws Exception {
//        final CountDownLatch readSignal = new CountDownLatch(4);
//        numOfUsers = 0;
//
//        databaseReference.child("rank").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                numOfUsers++;
//                readSignal.countDown();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        readSignal.await(1, TimeUnit.SECONDS);
//        assertEquals(5, numOfUsers);
//    }
//
//
////    @Test
////    public void testAddAUser() throws Exception {
////        final CountDownLatch writeSignal = new CountDownLatch(1);
////
////        databaseReference.child("users/UID").setValue(testUser).
////                addOnCompleteListener(new OnCompleteListener<Void>() {
////            @Override
////            public void onComplete(@NonNull Task<Void> task) {
////                writeSignal.countDown();
////            }
////        });
////    }
//
//
////    @Test
////    public void testAddToRank() throws Exception {
////        final CountDownLatch writeSignal = new CountDownLatch(1);
////
////        PracticeData testData = new PracticeData(date, "110");
////        testUser.addData(testData);
////
////        databaseReference.child("rank/" + testUser.getName()).setValue(testUser.getCurrentData()).
////                addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////                        writeSignal.countDown();
////                    }
////                });
////    }
//
////    @Test
////    public void testUpdateRecord() throws Exception {
////        final CountDownLatch writeSignal = new CountDownLatch(1);
////
////        PracticeData testData = new PracticeData(date, "0");
////        testUser.addData(testData);
////        databaseReference.child("users/UID/data/").setValue(testData).
////                addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////                        writeSignal.countDown();
////                    }
////                });
////    }
//
//    @Test
//    public void testUpdateTime() throws Exception {
//        final CountDownLatch writeSignal = new CountDownLatch(1);
//
//        double testTime = 120.6;
//        databaseReference.child("users/UID/data/0/practiceTime").setValue(testTime).
//                addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        writeSignal.countDown();
//                    }
//                });
//    }
//
//    @Test
//    public void testUpdateCurrentTime() throws Exception {
//        final CountDownLatch writeSignal = new CountDownLatch(1);
//
//        double testTime = 120.6;
//        databaseReference.child("users/UID/currentData").setValue(testTime).
//                addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        writeSignal.countDown();
//                    }
//                });
//    }
//
////    @Test
////    public void testUpdateRank() throws Exception {
////        final CountDownLatch writeSignal = new CountDownLatch(1);
////
////        double testTime = 120.6;
////        databaseReference.child("rank/" + testUser.getName()).setValue(testTime).
////                addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////                        writeSignal.countDown();
////                    }
////                });
////    }
//}
