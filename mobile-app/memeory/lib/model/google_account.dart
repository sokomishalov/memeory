class GoogleAccount {
  final String id;
  final String name;
  final String email;
  final String photo;

  GoogleAccount({this.id, this.name, this.email, this.photo});

  Map<String, dynamic> toJson() {
    return {
      "id": this.id,
      "name": this.name,
      "email": this.email,
      "photo": this.photo,
    };
  }

  factory GoogleAccount.fromJson(Map<String, dynamic> json) {
    return GoogleAccount(
      id: json["id"],
      name: json["name"],
      email: json["email"],
      photo: json["photo"],
    );
  }
}
