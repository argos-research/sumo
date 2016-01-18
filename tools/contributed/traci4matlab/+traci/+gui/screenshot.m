function screenshot(viewID, filename)
%screenshot Save a screenshot of the SUMO gui.
%   screenshot(VIEWID, FILENAME) Save a screenshot for the given view to 
%   the given filename. The fileformat is guessed from the extension, the 
%   available formats differ from platform to platform but should at least
%   include ps, svg and pdf, on linux probably gif, png and jpg as well.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: screenshot.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
traci.sendStringCmd(constants.CMD_SET_GUI_VARIABLE, constants.VAR_SCREENSHOT, viewID, filename);