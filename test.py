import tkinter as tk

# Create the window
window = tk.Tk()
window.title("Simple GUI")
window.geometry("300x150")

# Add a label
label = tk.Label(window, text="Enter your name:")
label.pack()

# Add an entry widget
entry = tk.Entry(window)
entry.pack()

# Add a button
def greet():
    name = entry.get()
    label.config(text=f"Hello, {name}!")

button = tk.Button(window, text="Greet", command=greet)
button.pack()

# Run the GUI event loop
window.mainloop()
