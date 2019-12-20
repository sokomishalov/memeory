import 'dart:convert';

import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/http/http.dart';

Future<List> fetchTopics() async {
  final baseUrl = getBackendUrl();
  final url = '${baseUrl}topics/list';
  final response = await http.get(url);
  return json.decode(utf8.decode(response.bodyBytes));
}
