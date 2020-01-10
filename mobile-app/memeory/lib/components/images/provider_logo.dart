import 'package:flutter/material.dart';
import 'package:memeory/api/providers.dart';
import 'package:memeory/components/images/network_logo.dart';

class ProviderLogo extends StatelessWidget {
  const ProviderLogo({
    Key key,
    this.providerId,
    this.size = 24,
  }) : super(key: key);

  final String providerId;
  final double size;

  @override
  Widget build(BuildContext context) {
    return NetworkLogo(
      url: getProviderLogoUrl(providerId),
      size: size,
    );
  }
}
