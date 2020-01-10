isEmpty(List<dynamic> list) {
  return list?.isEmpty ?? true;
}

isNotEmpty(List<dynamic> list) {
  return !isEmpty(list);
}
