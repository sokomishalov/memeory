import 'dart:convert';

import 'package:memeory/util/firebase/firebase.dart';
import 'package:memeory/util/http/http.dart';


Future<List> fetchChannels() async {
  final baseUrl = getBackendUrl();
  final url = '${baseUrl}channels/list/enabled';
  final response = await http.get(url);
  return json.decode(utf8.decode(response.bodyBytes));
}

Future<String> getLogoUrl(String channelId) async {
  final baseUrl = getBackendUrl();
  return '${baseUrl}channels/logo/$channelId';
}
