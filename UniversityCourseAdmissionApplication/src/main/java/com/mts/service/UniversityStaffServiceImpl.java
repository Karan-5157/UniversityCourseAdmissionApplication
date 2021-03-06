package com.mts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mts.entity.Course;
import com.mts.entity.UniversityStaffMember;
import com.mts.exception.CourseNotFoundException;
import com.mts.exception.StaffMemberNotFoundException;
import com.mts.repository.ICourseRepository;
import com.mts.repository.IUniversityStaffRepository;

@Service
public class UniversityStaffServiceImpl implements IUniversityStaffService{

	@Autowired
	IUniversityStaffRepository repo;
	
	@Autowired
	ICourseRepository courseRepository;
	
	@Override
	public UniversityStaffMember addStaff(UniversityStaffMember user) {
		return repo.save(user);
	}

	@Override
	public UniversityStaffMember updateStaff(UniversityStaffMember user) throws StaffMemberNotFoundException {
		UniversityStaffMember member=repo.findById(user.getStaffId()).orElseThrow(()->new StaffMemberNotFoundException("Cann't update. No staff member with this Id found!"));
		
		member.setPassword(user.getPassword());
		member.setRole(user.getRole());
		return repo.save(member);
	}

	@Override
	public UniversityStaffMember viewStaff(int staffid) throws StaffMemberNotFoundException {
		return repo.findById(staffid).orElseThrow(()->new StaffMemberNotFoundException("Invalid staffId !"));
	}

	@Override
	public void removeStaff(int staffid) throws StaffMemberNotFoundException {
		repo.deleteById(staffid);
	}

	@Override
	public List<UniversityStaffMember> viewAllStaffs() {
		return repo.findAll();
	}

	@Override
	public Course addCourse(Course course) {
		return courseRepository.save(course);
	}

	@Override
	public Course removeCourse(int courseId) throws CourseNotFoundException {
		Course course = courseRepository.findById(courseId).orElseThrow(()->new CourseNotFoundException("No record present with given id"));

		courseRepository.delete(course);
		return course;
	}

	@Override
	public Course updateCourse(Course course) throws CourseNotFoundException {
		Course existingCourse = courseRepository.findById(course.getCourseId()).orElseThrow(()->new CourseNotFoundException("Cann't update. No Course with this Id found!"));

		existingCourse.setCourseName(course.getCourseName());
		existingCourse.setCourseDuration(course.getCourseDuration());
		existingCourse.setCourseStartDate(course.getCourseStartDate());
		existingCourse.setCourseEndDate(course.getCourseEndDate());
		existingCourse.setCourseFees(course.getCourseFees());
		return courseRepository.save(existingCourse);
	}

}
