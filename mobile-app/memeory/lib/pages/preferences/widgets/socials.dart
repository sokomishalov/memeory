import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_facebook_login/flutter_facebook_login.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:gradient_text/gradient_text.dart';
import 'package:memeory/api/profile.dart';
import 'package:memeory/cache/repository/socials_repo.dart';
import 'package:memeory/components/message/messages.dart';
import 'package:memeory/strings/ru.dart';
import 'package:memeory/util/consts.dart';
import 'package:memeory/util/http.dart';
import 'package:pedantic/pedantic.dart';

final _googleSignIn = GoogleSignIn(
  scopes: ['email', 'https://www.googleapis.com/auth/userinfo.profile'],
);

final _facebookSignIn = FacebookLogin();
const facebookBaseUrl =
    'https://graph.facebook.com/v2.12/me?fields=name,first_name,last_name,email';

class SocialPreferences extends StatefulWidget {
  @override
  _SocialPreferencesState createState() => _SocialPreferencesState();
}

class _SocialPreferencesState extends State<SocialPreferences> {
  Map<String, dynamic> _googleProfile;
  Map<String, dynamic> _facebookProfile;

  @override
  void initState() {
    unawaited(_refreshProfiles());
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Container(
            child: RaisedButton(
              color: Colors.white,
              onPressed: () async => await _googleAuth(context),
              child: GradientText(
                _googleProfile == null
                    ? AUTH_GOOGLE
                    : _googleProfile["name"] ?? EMPTY,
                gradient: LinearGradient(colors: [
                  Color.fromRGBO(234, 67, 53, 1),
                  Color.fromRGBO(251, 188, 5, 1),
                  Color.fromRGBO(52, 168, 83, 1),
                  Color.fromRGBO(66, 133, 244, 1),
                ]),
                textAlign: TextAlign.center,
              ),
            ),
          ),
          Container(
            padding: EdgeInsets.only(top: 15),
            child: RaisedButton(
              color: Color.fromRGBO(66, 103, 178, 1),
              onPressed: () async => await _facebookAuth(context),
              child: Text(
                _facebookProfile == null
                    ? AUTH_FACEBOOK
                    : _facebookProfile["name"] ?? EMPTY,
                style: TextStyle(
                  color: Colors.white,
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  _refreshProfiles() async {
    var googleProfile = await getGoogleProfile();
    var facebookProfile = await getFacebookProfile();

    setState(() {
      _facebookProfile = facebookProfile;
      _googleProfile = googleProfile;
    });
  }

  _googleAuth(context) async {
    try {
      var signIn = await _googleSignIn.signIn();

      if (signIn == null) throw new FlutterError(EMPTY);

      var profile = {
        "id": signIn?.id,
        "name": signIn?.displayName,
        "email": signIn?.email,
        "photo": signIn?.photoUrl,
      };
      await setGoogleProfile(profile);

      await saveProfile();

      await _refreshProfiles();

      successToast("$WELCOME, ${signIn?.email}", context);
    } catch (e) {
      debugPrint(e.toString());
      errorToast(UNSUCCESSFUL_AUTH_GOOGLE, context);
    }
  }

  _facebookAuth(context) async {
    final result = await _facebookSignIn.logInWithReadPermissions(
      ['email'],
    );

    switch (result.status) {
      case FacebookLoginStatus.loggedIn:
        final token = result.accessToken.token;
        final profileResponse = await http.get(
          '$facebookBaseUrl&access_token=$token',
        );
        final profile = json.decode(profileResponse.body);
        await setFacebookProfile(profile);

        await saveProfile();

        await _refreshProfiles();

        successToast("$WELCOME, $profile", context);

        break;
      case FacebookLoginStatus.cancelledByUser:
      case FacebookLoginStatus.error:
        debugPrint(result.toString());
        errorToast(UNSUCCESSFUL_AUTH_FACEBOOK, context);
        break;
    }
  }
}
