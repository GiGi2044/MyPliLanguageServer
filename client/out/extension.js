"use strict";
/* --------------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */
Object.defineProperty(exports, "__esModule", { value: true });
const path = require("path");
// Import the language client, language client options and server options from VSCode language client.
const vscode_languageclient_1 = require("vscode-languageclient");
// Name of the launcher class which contains the main.
const main = 'StdioLauncher';
function activate(context) {
    console.log('Congratulations, your "PL/1" extension is now active!');
    // Get the java home from the process environment.
    const { JAVA_HOME } = process.env;
    console.log(`Using java from JAVA_HOME: ${JAVA_HOME}`);
    // If java home is available continue.
    if (JAVA_HOME) {
        // Java execution path.
        let excecutable = path.join(JAVA_HOME, 'bin', 'java');
        // path to the launcher.jar
        let classPath = path.join(__dirname, '..', 'launcher', 'launcher.jar');
        const args = ['-cp', classPath];
        // Set the server options
        // -- java execution path
        // -- argument to be pass when executing the java command
        let serverOptions = {
            command: excecutable,
            args: [...args, main],
            options: {}
        };
        // Options to control the language client
        let clientOptions = {
            // Register the server for plain text documents
            documentSelector: [{ scheme: 'file', language: 'pli' }]
        };
        // Create the language client and start the client.
        let disposable = new vscode_languageclient_1.LanguageClient('pli', 'pli Language Server', serverOptions, clientOptions).start();
        // Disposables to remove on deactivation.
        context.subscriptions.push(disposable);
    }
}
exports.activate = activate;
// this method is called when your extension is deactivated
function deactivate() {
    console.log('Your "PL/1" extension is now deactivated!');
}
exports.deactivate = deactivate;
//# sourceMappingURL=extension.js.map