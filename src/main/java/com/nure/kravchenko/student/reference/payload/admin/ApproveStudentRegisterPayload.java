package com.nure.kravchenko.student.reference.payload.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveStudentRegisterPayload {
    private String groupName;

}
