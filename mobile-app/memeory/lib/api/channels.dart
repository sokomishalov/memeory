import 'dart:convert';

import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/http/http.dart';

Future<List> fetchChannels() async {
  final baseUrl = getBackendUrl();
  final url = '${baseUrl}channels/list';
  final response = await http.get(url);
  return json.decode(utf8.decode(response.bodyBytes));
}

String getChannelLogoUrl(String channelId) {
  final baseUrl = getBackendUrl();
  return '${baseUrl}channels/logo/$channelId';
}
