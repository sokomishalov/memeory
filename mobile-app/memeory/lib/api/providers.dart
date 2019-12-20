import 'dart:convert';

import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/http/http.dart';

Future<List> fetchProviders() async {
  final baseUrl = getBackendUrl();
  final url = '${baseUrl}providers/list';
  final response = await http.get(url);
  return json.decode(utf8.decode(response.bodyBytes));
}

String getProviderLogoUrl(String providerId) {
  final baseUrl = getBackendUrl();
  return '${baseUrl}providers/logo/$providerId';
}
