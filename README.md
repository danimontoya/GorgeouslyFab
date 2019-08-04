# GorgeouslyFab

TechStack: Clean Architecture, MVVM, Dagger2, Coroutines, LiveData, Arrow, Groupie and unit tests.

GorgeouslyFab is personal clothing gallery for influencers. For this first prototype, we simply need a feature that creates reviews for garments.

A review requires the following details:
- Garment: For the time being, we only support Trousers, Jeans, Shirts, T-Shirts, Jumpers, Coats and Shoes.
- Designer: Free text. Maximum length: 80 characters.
- Feel: Free text used to describe whether this size is loose or fitted, feel of the fabric, etc.
- Picture: Selfie wearing the product.

The user should be able to create unlimited reviews and these would be browsable on a list. For this prototype, storing the list of reviews is not required (this means the list of reviews would be cleared every time the app is killed).

Lastly, our UX experts expect a process of atomic steps that allow introducing each field of the review individually. In other words, a screen with garment input field is displayed. When tapping the 'Continue' button, that screen disappears and a new screen shows up asking for the designer field. Then tapping on the 'Continue' button would lead to the following step. On the last step, the review is created.

When the app is running on a tablet, however, we would like to have all steps one after another on the same screen (form-like screen).

What we would love to see from you:
- Use of software patterns and appropriate software architecture.
- Making a statement with your great UI and smooth user experience.
- Minding phone/tablet and portrait/landscape mode support - persist state so the user never loses what's on screen!
- Keeping quality in mind: automate testing! Look after unit and UI testing.

Is there anything you think you're really good at? Show off! We would be glad to see what you can do!

If you make any trade-off (i.e.: you skip repetitive unit tests), please include the most meaningful cases and let us know of any concession or design decision you make. This would be much appreciated and would help us understand your thinking!
