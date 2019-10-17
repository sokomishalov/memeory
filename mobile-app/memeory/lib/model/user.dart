import 'package:firebase_auth/firebase_auth.dart';

class ProviderAuth {
  String uid;
  String displayName;
  String photoURL;
  String email;
  String phoneNumber;

  ProviderAuth({
    this.uid,
    this.displayName,
    this.photoURL,
    this.email,
    this.phoneNumber,
  });

  Map<String, dynamic> toJson() {
    return {
      "uid": this.uid,
      "displayName": this.displayName,
      "photoURL": this.photoURL,
      "email": this.email,
      "phoneNumber": this.phoneNumber,
    };
  }

  factory ProviderAuth.fromJson(Map<String, dynamic> json) {
    return ProviderAuth(
      uid: json["uid"],
      displayName: json["displayName"],
      photoURL: json["photoURL"],
      email: json["email"],
      phoneNumber: json["phoneNumber"],
    );
  }

  factory ProviderAuth.fromFirebaseUser(FirebaseUser user) {
    return ProviderAuth(
      uid: user.uid,
      displayName: user.displayName,
      photoURL: user.photoUrl,
      email: user.email,
      phoneNumber: user.phoneNumber,
    );
  }
}
