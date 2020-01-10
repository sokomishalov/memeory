import 'dart:convert';

import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/http/http.dart';

Future<List<String>> fetchProviders() async {
  final baseUrl = getBackendUrl();
  final url = '${baseUrl}providers/list';
  final response = await http.get(url);
  var decodedData = json.decode(utf8.decode(response.bodyBytes));
  return List.of(decodedData).map((it) => it.toString()).toList();
}

String getProviderLogoUrl(String providerId) {
  final baseUrl = getBackendUrl();
  return '${baseUrl}providers/logo/$providerId';
}
