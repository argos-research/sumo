function boundary = getBoundary(viewID)
%getBoundary Get the coordinates of the view.
%   boundary = getBoundary(VIEWID) Returns the coordinates of the lower 
%   left and the upper right corner of the currently visible view. If no
%   view ID is given, the function return the results for the default view.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getBoundary.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
if nargin < 1
    viewID = 'View #0';
end
boundary = traci.gui.getUniversal(constants.VAR_VIEW_BOUNDARY, viewID);