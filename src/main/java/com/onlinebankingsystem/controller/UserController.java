package com.onlinebankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onlinebankingsystem.dto.CommonApiResponse;
import com.onlinebankingsystem.dto.RegisterUserRequestDto;
import com.onlinebankingsystem.dto.UserListResponseDto;
import com.onlinebankingsystem.dto.UserLoginRequest;
import com.onlinebankingsystem.dto.UserLoginResponse;
import com.onlinebankingsystem.dto.UserProfileUpdateDto;
import com.onlinebankingsystem.dto.UserStatusUpdateRequestDto;
import com.onlinebankingsystem.entity.User;
import com.onlinebankingsystem.resource.UserResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/user/")
@CrossOrigin
public class UserController {

	@Autowired
	private UserResource userResource;

	// for customer and bank register
	@PostMapping("register")
	@Operation(summary = "Api to register customer or bank user")
	public ResponseEntity<CommonApiResponse> registerUser(@RequestBody RegisterUserRequestDto request) {
		return this.userResource.registerUser(request);
	}

	// RegisterUserRequestDto, we will set only email, password & role from UI
	@PostMapping("/admin/register")
	public ResponseEntity<CommonApiResponse> registerAdmin(@RequestBody RegisterUserRequestDto request) {
		return userResource.registerAdmin(request);
	}

	@PostMapping("login")
	@Operation(summary = "Api to login any User")
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		return userResource.login(userLoginRequest);
	}

	@GetMapping("/fetch/role")
	@Operation(summary = "Api to get Users By Role")
	public ResponseEntity<UserListResponseDto> fetchAllBankUsers(@RequestParam("role") String role) {
		return userResource.getUsersByRole(role);
	}

	@GetMapping("/fetch/id")
	@Operation(summary = "Api to get Users By id")
	public ResponseEntity<UserListResponseDto> fetchById(@RequestParam("id") int id) {
		return userResource.fetchById(id);
	}

	@GetMapping("/fetch/customer/pending/request")
	@Operation(summary = "Api to get Users By Role")
	public ResponseEntity<UserListResponseDto> fetchPendingCustomers() {
		return userResource.fetchPendingCustomers();
	}

	@GetMapping("/fetch/bank/managers")
	@Operation(summary = "Api to get Bank Managers who is not assigned to any other Bank")
	public ResponseEntity<UserListResponseDto> fetchBankManagers() {
		return userResource.fetchBankManagers();
	}

	@PostMapping("update/status")
	@Operation(summary = "Api to update the user status")
	public ResponseEntity<CommonApiResponse> updateUserStatus(@RequestBody UserStatusUpdateRequestDto request) {
		return userResource.updateUserStatus(request);
	}

	@GetMapping("/bank/customers")
	@Operation(summary = "Api to get Bank Customers by bank id")
	public ResponseEntity<UserListResponseDto> fetchAllBankCustomersByBankId(@RequestParam("bankId") int bankId) {
		return userResource.fetchBankCustomerByBankId(bankId);
	}

//	@GetMapping("/bank/customer/search")
//	@Operation(summary =  "Api to get Bank Customers by bank id")
//	public ResponseEntity<UserListResponseDto> searchBankCustomer(@RequestParam("bankId") int bankId, @RequestParam("customerName") String customerName) {
//		return userResource.searchBankCustomer(bankId, customerName);
//	}

	@GetMapping("/all/customer/search")
	@Operation(summary = "Api to get all Bank Customers by customer name")
	public ResponseEntity<UserListResponseDto> searchBankCustomer(@RequestParam("customerName") String customerName) {
		return userResource.searchBankCustomer(customerName);
	}

	@PutMapping("/update/profile")
	@Operation(summary = "Api to update the user profile")
	public ResponseEntity<CommonApiResponse> updateProfile(@RequestBody RegisterUserRequestDto request) {
		return this.userResource.updateUserprofile(request);
	}

	@PostMapping("/send/reset-password/mail")
	public ResponseEntity<CommonApiResponse> forgetPassword(@RequestBody UserLoginRequest request) {
		return userResource.forgetPassword(request);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<CommonApiResponse> resetPassword(@RequestBody UserLoginRequest request) {
		return userResource.resetPassword(request);
	}

	
// Add By Prince	
	@PostMapping("/update-profile")
	public ResponseEntity<CommonApiResponse> updateUserData(@RequestBody UserProfileUpdateDto request) {
		System.out.println(" hallo");
	    return userResource.updateUserData(request);
	}
	
    @PostMapping("/upload-profile-image")
    public ResponseEntity<CommonApiResponse> uploadProfileImage(@RequestParam("userId") Long userId, @RequestParam("image") MultipartFile image) {
        return userResource.uploadProfileImage(userId, image);
    }
}
