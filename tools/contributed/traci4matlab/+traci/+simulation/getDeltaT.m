function deltaT = getDeltaT()
%getDeltaT Returns the time-step of the simulation in ms.
%   deltaT = getDeltaT() Returns the time-step of the simulation in ms. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getDeltaT.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
deltaT = traci.simulation.getUniversal(constants.VAR_DELTA_T);