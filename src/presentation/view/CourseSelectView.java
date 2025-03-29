package presentation.view;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import business.dto.Course;
import repository.dao.CourseDao;
import repository.dao.CourseDaoImpl;

public class CourseSelectView {
    private final Scanner sc = new Scanner(System.in);
    private final CourseDao courseDao = new CourseDaoImpl();

    public int selectCourse() {
        while (true) {
            System.out.println("===== 과정 선택 화면 =====");
            List<Course> courses = courseDao.getAllCourses();

            if (courses.isEmpty()) {
                System.out.println("등록된 과정이 없습니다. 관리자에게 문의하세요.");
                return -1;
            }

            System.out.println("과정 목록:");
            for (Course course : courses) {
                System.out.println("[ ID: " + course.getCourseUid()+ " ] " + 
                                   " - [ 이름:  " + course.getCourseName()+ " ] " + 
                                   " - [ 수강 가능 여부: " + (course.getCourse_open() == 1 ? "가능 ]" : "불가능 ]"));
            }

            System.out.print("선택할 과정 ID를 입력하세요 (취소하려면 0 입력): ");
            String input = sc.nextLine();

            try {
                int courseUid = Integer.parseInt(input);
                if (courseUid == 0) {
                    System.out.println("과정 선택이 취소되었습니다.");
                    return -1;
                }

                boolean valid = courses.stream().anyMatch(course -> course.getCourseUid() == courseUid);
                if (valid) {
                    System.out.println("과정 선택이 완료되었습니다.");
                    return courseUid;
                } else {
                    System.out.println("잘못된 과정 ID입니다. 다시 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }

    public List<Integer> selectCoursesForRegistration() {
        List<Integer> selectedCourseIds = new ArrayList<>();

        while (true) {
            System.out.println("===== 회원가입 과정 선택 화면 =====");
            List<Course> courses = courseDao.getAllCourses();

            if (courses.isEmpty()) {
                System.out.println("등록된 과정이 없습니다. 관리자에게 문의하세요.");
                return selectedCourseIds;
            }

            System.out.println("과정 목록:");
            for (Course course : courses) {
                System.out.println("[ ID: " + course.getCourseUid() + " ] " +
                                   " - [ 이름: " + course.getCourseName() + " ] " +
                                   " - [ 수강 가능 여부: " + (course.getCourse_open() == 1 ? "가능 ]" : "불가능 ]"));
            }

            System.out.print("선택할 과정 ID를 입력하세요 (선택 완료 후 0 입력): ");
            String input = sc.nextLine();

            try {
                int courseUid = Integer.parseInt(input);
                if (courseUid == 0) {
                    System.out.println("과정 선택이 완료되었습니다.");
                    break;
                }

                boolean valid = courses.stream().anyMatch(course -> course.getCourseUid() == courseUid);
                if (valid) {
                    if (!selectedCourseIds.contains(courseUid)) {
                        selectedCourseIds.add(courseUid);
                        System.out.println("과정이 추가되었습니다.");
                    } else {
                        System.out.println("이미 선택된 과정입니다.");
                    }
                } else {
                    System.out.println("잘못된 과정 ID입니다. 다시 입력해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }

        return selectedCourseIds;
    }
}
