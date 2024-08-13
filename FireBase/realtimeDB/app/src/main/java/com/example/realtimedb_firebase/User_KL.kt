package com.example.realtimedb_firebase

data class User_KL(
      var id: Int = 0,         // Giá trị mặc định để tránh lỗi nếu Firebase không cung cấp dữ liệu
      var name: String = ""    // Giá trị mặc định để tránh lỗi nếu Firebase không cung cấp dữ liệu
) {
      // Constructor không tham số cần thiết cho Firebase
      constructor() : this(0, "")

      fun toMap() :Map<String, Any>{
            return mapOf(
               //   "id" to id,
                  "name" to name
            )
      }
}
