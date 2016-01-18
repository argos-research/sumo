function lanePosition = getLanePosition(personID)
%getLanePosition Get the position of the person along the lane.
%   lanePosition = getLanePosition(PERSONID) Returns the position of the 
%   person along the lane measured in m.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLanePosition.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
lanePosition = traci.person.getUniversal(constants.VAR_LANEPOSITION, personID);