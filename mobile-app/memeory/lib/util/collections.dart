isEmpty(List<dynamic> list) {
  return list?.isEmpty ?? true;
}

isNotEmpty(List<dynamic> list) {
  return !isEmpty(list);
}

List<dynamic> distinctByProperty(List<dynamic> list, String property) {
  return list
      .map((it) => it["id"])
      .toSet()
      .map((it) => list.firstWhere((o) => o["id"] == it))
      .toList();
}
