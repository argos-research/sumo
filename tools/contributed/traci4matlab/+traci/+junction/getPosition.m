function position = getPosition(junctionID)
%getPosition Get the position of the junction.
%   position = getPosition(JUNCTIONID) Returns the coordinates of the 
%   center of the junction.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getPosition.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
position = traci.junction.getUniversal(constants.VAR_POSITION, junctionID);