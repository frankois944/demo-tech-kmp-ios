//
//  SharedViewModel.swift
//  DestinationGuide
//
//  Created by Francois Dabonot on 28/04/2025.
//

import Shared

/// Syncing the KMP viewmodel with the lifecycle of the ViewController
class SharedViewModel<VM: ViewModel> {

    private let key = String(describing: type(of: VM.self))
    private let viewModelStore = ViewModelStore()

    // Injecting the viewmodel
    init(_ viewModel: VM = .init()) {
        viewModelStore.put(key: key, viewModel: viewModel)
    }

    var instance: VM {
        (viewModelStore.get(key: key) as? VM)!
    }

    deinit {
        viewModelStore.clear()
    }
}
