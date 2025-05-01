//
//  RecentDestinationButton.swift
//  DestinationGuide
//
//  Created by Francois Dabonot on 01/05/2025.
//

import UIKit

class RecentDestinationButton: UIButton {

    // MARK: Properties
    private var customConfiguration = UIButton.Configuration.plain()

    // MARK: - Init

    override init(frame: CGRect) {
        super.init(frame: frame)
        setStyle()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setStyle()
    }

    // MARK: - Style

    private func setStyle() {
        customConfiguration.contentInsets = NSDirectionalEdgeInsets(top: 8, leading: 8, bottom: 8, trailing: 8)
        self.configuration = customConfiguration
        layer.borderColor = UIColor.black.cgColor
        layer.borderWidth = 1
        layer.cornerRadius = 16
    }

    override func setTitle(_ title: String?, for state: UIControl.State) {
        let attributes = [
            NSAttributedString.Key.font: UIFont.avertaSemibold(fontSize: 16),
            NSAttributedString.Key.foregroundColor: UIColor.black
        ]
        let content = NSAttributedString.init(string: (title ?? ""), attributes: attributes)
        super.setAttributedTitle(content, for: state)
    }
}
