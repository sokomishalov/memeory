enum MemesOrientation {
  VERTICAL,
  HORIZONTAL,
}

MemesOrientation orientationFromString(String str) {
  return MemesOrientation.values.firstWhere(
      (e) => e.toString() == 'MemesOrientation.$str',
      orElse: () => MemesOrientation.VERTICAL);
}
