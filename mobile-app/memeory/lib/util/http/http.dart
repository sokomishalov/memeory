import 'dart:io';

import 'package:http/io_client.dart';

final http = IOClient(
  HttpClient()..badCertificateCallback = (_, __, ___) => true,
);
