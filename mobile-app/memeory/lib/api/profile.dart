import 'dart:convert';

import 'package:memeory/util/env.dart';
import 'package:memeory/util/http.dart';

Future saveProfile() async {
  // todo
  final url = '${env.backendUrl}/profile/save';
  final response = await http.post(url, body: {}, headers: {});
  return json.decode(response.body);
}
