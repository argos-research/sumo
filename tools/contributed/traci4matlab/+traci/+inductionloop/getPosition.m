function position = getPosition(loopID)
%getPosition Get the position of the loop.
%   position = getPosition(LOOPID) Returns the position measured from the 
%   beginning of the lane in meters.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
position = traci.inductionloop.getUniversal(constants.VAR_POSITION, loopID);