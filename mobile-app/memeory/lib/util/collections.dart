List<dynamic> distinctByProperty(List<dynamic> list, String property) {
  return list
      .map((it) => it["id"])
      .toSet()
      .map((it) => list.firstWhere((o) => o["id"] == it))
      .toList();
}
