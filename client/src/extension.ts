
/* --------------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 * ------------------------------------------------------------------------------------------ */

import * as path from 'path';
import * as vscode from 'vscode';

// Import the language client, language client options and server options from VSCode language client.
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient';

// Name of the launcher class which contains the main.
const main: string = 'StdioLauncher';

export function activate(context: vscode.ExtensionContext) {
	console.log('Congratulations, your "PL/1" extension is now active!');

	// Get the java home from the process environment.
	const { JAVA_HOME } = process.env;

	console.log(`Using java from JAVA_HOME: ${JAVA_HOME}`);
	// If java home is available continue.
	if (JAVA_HOME) {
		// Java execution path.
		let excecutable: string = path.join(JAVA_HOME, 'bin', 'java');

		// path to the launcher.jar
		let classPath = path.join(__dirname, '..', 'launcher', 'launcher.jar');
		const args: string[] = ['-cp', classPath];

		// Set the server options
		// -- java execution path
		// -- argument to be pass when executing the java command
		let serverOptions: ServerOptions = {
			command: excecutable,
			args: [...args, main],
			options: {}
		};

		// Options to control the language client
		let clientOptions: LanguageClientOptions = {
			// Register the server for plain text documents
			documentSelector: [{ scheme: 'file', language: 'pli' }]
		};

		// Create the language client and start the client.
		let disposable = new LanguageClient('pli', 'pli Language Server', serverOptions, clientOptions).start();

		// Disposables to remove on deactivation.
		context.subscriptions.push(disposable);
	}
}

// this method is called when your extension is deactivated
export function deactivate() {
	console.log('Your "PL/1" extension is now deactivated!');
}
