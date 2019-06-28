import 'dart:convert';

import 'package:memeory/util/env.dart';
import 'package:memeory/util/http.dart';

Future<List> fetchChannels() async {
  final url = '${env.backendUrl}/channels/list/enabled';
  final response = await http.get(url);
  return json.decode(utf8.decode(response.bodyBytes));
}
