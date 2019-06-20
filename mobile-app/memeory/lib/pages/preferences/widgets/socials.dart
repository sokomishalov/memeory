import 'package:flutter/material.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:memeory/cache/repository/socials_repo.dart';
import 'package:memeory/common/message/messages.dart';
import 'package:memeory/model/google_account.dart';

final _googleSignIn = GoogleSignIn(
  scopes: [
    'email',
    'https://www.googleapis.com/auth/userinfo.profile',
  ],
);

class SocialPreferences extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          RaisedButton(
            onPressed: () async {
              try {
                var signIn = await _googleSignIn.signIn();

                if (signIn == null) throw new FlutterError("");

                successToast("Добро пожаловать, ${signIn?.email}", context);

                var profile = GoogleAccount(
                  id: signIn?.id,
                  name: signIn?.displayName,
                  email: signIn?.email,
                  photo: signIn?.photoUrl,
                );
                await setGoogleProfile(profile);
              } catch (e) {
                debugPrint(e.toString());
                errorToast("Неудачная попытка авторизации в Google", context);
              }
            },
            child: Text("Google Auth"),
          )
        ],
      ),
    );
  }
}
