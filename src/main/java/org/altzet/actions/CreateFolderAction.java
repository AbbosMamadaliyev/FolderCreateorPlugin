package org.altzet.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CreateFolderAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Tanlangan folderni olish
        VirtualFile selectedDir = event.getData(CommonDataKeys.VIRTUAL_FILE);
        if (selectedDir == null || !selectedDir.isDirectory()) {
            Messages.showErrorDialog("Please select a folder!", "Error");
            return;
        }

        // Foydalanuvchidan yangi folder nomini so'rash
        String folderName = Messages.showInputDialog(
                "Enter the new folder name:",
                "Create TDD Folder",
                Messages.getQuestionIcon(),
                "",
                new InputValidator() {
                    @Override
                    public boolean checkInput(String inputString) {
                        return inputString != null && !inputString.trim().isEmpty();
                    }
                    @Override
                    public boolean canClose(String inputString) {
                        return true;
                    }
                }
        );

        if (folderName == null || folderName.trim().isEmpty()) {
            return;
        }

        // Nomning lower-case va capitalized variantlarini tayyorlash
        String folderNameLower = folderName.toLowerCase();
        String folderNameCapitalized = folderName.substring(0, 1).toUpperCase() + folderName.substring(1);

        // Fayl tizimi o'zgarishlarini write action ichida bajarish
        try {
            ApplicationManager.getApplication().runWriteAction(() -> {
                try {
                    // Asosiy folderni yaratish
                    VirtualFile mainFolder = selectedDir.createChildDirectory(this, folderName);

                    // 1. "data" folderi va uning subfolderlari: datasources, repositories, models
                    VirtualFile dataFolder = mainFolder.createChildDirectory(this, "data");
                    VirtualFile datasourcesFolder = createSubfolder(dataFolder, "datasources");
                    VirtualFile dataRepositoriesFolder = createSubfolder(dataFolder, "repositories");
                    VirtualFile modelsFolder = createSubfolder(dataFolder, "models");

                    // 2. "domain" folderi va uning subfolderlari: repositories, entities, usecases
                    VirtualFile domainFolder = mainFolder.createChildDirectory(this, "domain");
                    VirtualFile domainRepositoriesFolder = createSubfolder(domainFolder, "repositories");
                    VirtualFile domainEntitiesFolder = createSubfolder(domainFolder, "entities");
                    createSubfolder(domainFolder, "usecases");

                    // 3. "presentation" folderi va uning subfolderlari: blocs, pages, widgets
                    VirtualFile presentationFolder = mainFolder.createChildDirectory(this, "presentation");
                    createSubfolder(presentationFolder, "blocs");
                    createSubfolder(presentationFolder, "pages");
                    createSubfolder(presentationFolder, "widgets");

                    // Yaratilayotgan papka nomiga mos dart fayllarni generatsiya qilish:

                    // a. data/datasources ichida: <folderName>_datasource.dart
                    if (datasourcesFolder != null) {
                        VirtualFile datasourceFile = datasourcesFolder.createChildData(this, folderNameLower + "_datasource.dart");
                        String className = folderNameCapitalized + "Datasource";
                        String implClassName = folderNameCapitalized + "DatasourceImpl";
                        String modelName = folderNameCapitalized + "Model";
                        String methodName = folderNameLower;
                        String datasourceContent = "abstract class " + className + " {\n" +
                                "  Future<" + modelName + "> " + methodName + "();\n" +
                                "}\n\n" +
                                "class " + implClassName + " implements " + className + " {\n" +
                                "  @override\n" +
                                "  Future<" + modelName + "> " + methodName + "() async {\n" +
                                "    // TODO: implement " + methodName + "\n" +
                                "    throw UnimplementedError();\n" +
                                "  }\n" +
                                "}\n";
                        VfsUtil.saveText(datasourceFile, datasourceContent);
                    }

                    // b. data/repositories ichida: <folderName>_repository_impl.dart
                    if (dataRepositoriesFolder != null) {
                        VirtualFile repositoryImplFile = dataRepositoriesFolder.createChildData(this, folderNameLower + "_repository_impl.dart");
                        String repositoryImplContent = "import '../../domain/repositories/" + folderNameLower + "_repository.dart';\n\n" +
                                "class " + folderNameCapitalized + "RepositoryImpl implements " + folderNameCapitalized + "Repository {\n" +
                                "  @override\n" +
                                "  Future<Either<Failure, dynamic>> auth() {\n" +
                                "    // TODO: implement auth\n" +
                                "    throw UnimplementedError();\n" +
                                "  }\n" +
                                "}\n";
                        VfsUtil.saveText(repositoryImplFile, repositoryImplContent);
                    }

                    // c. domain/repositories ichida: <folderName>_repository.dart
                    if (domainRepositoriesFolder != null) {
                        VirtualFile repositoryFile = domainRepositoriesFolder.createChildData(this, folderNameLower + "_repository.dart");
                        String repositoryContent = "abstract class " + folderNameCapitalized + "Repository {\n" +
                                "  Future<Either<Failure, " + folderNameCapitalized + "Model>> auth();\n" +
                                "}\n";
                        VfsUtil.saveText(repositoryFile, repositoryContent);
                    }

                    // d. domain/entities ichida: <folderName>_entity.dart
                    if (domainEntitiesFolder != null) {
                        VirtualFile entityFile = domainEntitiesFolder.createChildData(this, folderNameLower + "_entity.dart");
                        String entityContent = "import 'package:equatable/equatable.dart';\n\n" +
                                "class " + folderNameCapitalized + "Entity extends Equatable {\n" +
                                "  final String login;\n\n" +
                                "  const " + folderNameCapitalized + "Entity({\n" +
                                "    this.login = '',\n" +
                                "  });\n\n" +
                                "  @override\n" +
                                "  List<Object?> get props => [\n" +
                                "        login,\n" +
                                "      ];\n" +
                                "}\n";
                        VfsUtil.saveText(entityFile, entityContent);
                    }

                    // e. data/models ichida: <folderName>_model.dart
                    if (modelsFolder != null) {
                        VirtualFile modelFile = modelsFolder.createChildData(this, folderNameLower + "_model.dart");
                        String modelContent = "import '../../domain/entities/" + folderNameLower + "_entity.dart';\n\n" +
                                "part '" + folderNameLower + "_model.g.dart';\n\n" +
                                "class " + folderNameCapitalized + "Model extends " + folderNameCapitalized + "Entity {\n" +
                                "  const " + folderNameCapitalized + "Model({\n" +
                                "    super.login,\n" +
                                "  });\n\n" +
                                "  factory " + folderNameCapitalized + "Model.fromJson(Map<String, dynamic> json) => _\\$" + folderNameCapitalized + "ModelFromJson(json);\n\n" +
                                "  Map<String, dynamic> toJson() => _\\$" + folderNameCapitalized + "ModelToJson(this);\n" +
                                "}\n";
                        VfsUtil.saveText(modelFile, modelContent);
                    }

                    // f. presentation ichida: <folderName>_screen.dart
                    if (presentationFolder != null) {
                        VirtualFile screenFile = presentationFolder.createChildData(this, folderNameLower + "_screen.dart");
                        String screenContent = "class " + folderNameCapitalized + "Screen extends StatefulWidget {\n" +
                                "  const " + folderNameCapitalized + "Screen({super.key});\n\n" +
                                "  @override\n" +
                                "  State<" + folderNameCapitalized + "Screen> createState() => _" + folderNameCapitalized + "ScreenState();\n" +
                                "}\n\n" +
                                "class _" + folderNameCapitalized + "ScreenState extends State<" + folderNameCapitalized + "Screen> {\n" +
                                "  @override\n" +
                                "  Widget build(BuildContext context) {\n" +
                                "    return const Placeholder();\n" +
                                "  }\n" +
                                "}\n";
                        VfsUtil.saveText(screenFile, screenContent);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Messages.showInfoMessage("Folder created: " + folderName, "Successful");
        } catch (RuntimeException re) {
            Messages.showErrorDialog("An error occurred while creating the folder:\n" + re.getMessage(), "Error");
        }
    }

    // Yordamchi metod: Berilgan ota papka ichida nomi berilgan subfolderni yaratadi va uning VirtualFile obyektini qaytaradi.
    private VirtualFile createSubfolder(VirtualFile parent, String name) throws IOException {
        return VfsUtil.createDirectoryIfMissing(parent.getPath() + "/" + name);
    }
}
