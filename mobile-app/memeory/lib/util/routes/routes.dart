enum ROUTES {
  INITIAL,
  PREFERENCES,
  MEMES,
}

extension RoutesExtension on ROUTES {
  String get route {
    switch (this) {
      case ROUTES.INITIAL:
        return '/';
      case ROUTES.PREFERENCES:
        return '/preferences';
      case ROUTES.MEMES:
      default:
        return '/memes';
    }
  }
}
