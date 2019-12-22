enum ROUTES {
  PREFERENCES,
  MEMES,
}

extension RoutesExtension on ROUTES {
  String get route {
    switch (this) {
      case ROUTES.PREFERENCES:
        return '/preferences';
      case ROUTES.MEMES:
      default:
        return '/memes';
    }
  }
}
