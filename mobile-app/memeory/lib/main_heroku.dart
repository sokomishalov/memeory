import 'package:memeory/app.dart';
import 'package:memeory/util/env/env.dart';

void main() {
  BuildEnvironment.init(flavor: BuildFlavor.heroku);

  runMemeory();
}
