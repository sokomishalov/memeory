import 'package:firebase_auth/firebase_auth.dart';

class ProviderAuth {
  String uid;
  String displayName;
  String photoUrl;
  String email;
  String phoneNumber;

  ProviderAuth({
    this.uid,
    this.displayName,
    this.photoUrl,
    this.email,
    this.phoneNumber,
  });

  Map<String, dynamic> toJson() {
    return {
      "uid": this.uid,
      "displayName": this.displayName,
      "photoUrl": this.photoUrl,
      "email": this.email,
      "phoneNumber": this.phoneNumber,
    };
  }

  factory ProviderAuth.fromJson(Map<String, dynamic> json) {
    return ProviderAuth(
      uid: json["uid"],
      displayName: json["displayName"],
      photoUrl: json["photoUrl"],
      email: json["email"],
      phoneNumber: json["phoneNumber"],
    );
  }

  factory ProviderAuth.fromFirebaseUser(FirebaseUser user) {
    return ProviderAuth(
      uid: user.uid,
      displayName: user.displayName,
      photoUrl: user.photoUrl,
      email: user.email,
      phoneNumber: user.phoneNumber,
    );
  }
}
