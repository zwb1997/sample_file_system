export function getFileType(fileName) {
  var models = fileName ? fileName.split(".") : [];

  return models.length > 1 ? models[models.length - 1] : "unknown";
}
